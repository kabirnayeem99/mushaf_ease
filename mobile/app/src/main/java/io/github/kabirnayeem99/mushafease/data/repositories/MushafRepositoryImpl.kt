package io.github.kabirnayeem99.mushafease.data.repositories

import android.util.Log
import android.util.LruCache
import io.github.kabirnayeem99.mushafease.data.mappers.toAyahList
import io.github.kabirnayeem99.mushafease.data.mappers.toSurahList
import io.github.kabirnayeem99.mushafease.data.service.database.dao.AyahDao
import io.github.kabirnayeem99.mushafease.data.service.database.dao.SurahDao
import io.github.kabirnayeem99.mushafease.domain.entities.Error
import io.github.kabirnayeem99.mushafease.domain.entities.Loading
import io.github.kabirnayeem99.mushafease.domain.entities.MushafPage
import io.github.kabirnayeem99.mushafease.domain.entities.Resource
import io.github.kabirnayeem99.mushafease.domain.entities.Success
import io.github.kabirnayeem99.mushafease.domain.entities.Surah
import io.github.kabirnayeem99.mushafease.domain.repositories.MushafRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MushafRepositoryImpl @Inject constructor(
    private val surahDao: SurahDao,
    private val ayahDao: AyahDao,
) : MushafRepository {
    override fun surahs() = surahDao.surahs().map { s -> s.toSurahList() }.map { surahList ->
        if (surahList.isEmpty()) Error() else Success(surahList)
    }.catch { e ->
        Log.e(TAG, "surahs: ", e)
        emit(Error())
    }.flowOn(Dispatchers.IO)


    private val ayahByPageCache = LruCache<Int, MushafPage>(30)

    override fun ayahByPage(pageNumber: Int): Flow<Resource<MushafPage>> {
        return flow {
            emit(Loading())
            val ayahDtoList = ayahDao.getAyahsFromPageSync(pageNumber)
            val ayahList = ayahDtoList.toAyahList()
            val surahIdUnique = ayahDtoList.map { a -> a.surahId }.distinct()
            val surahs = surahDao.surahsByIds(surahIdUnique.toIntArray()).toSurahList()
            val surahMap = mutableMapOf<Int, Surah>()
            surahs.forEach { surah -> surahMap[surah.number] = surah }
            val juz = ayahDtoList.firstOrNull()?.juz ?: 0
            val page = MushafPage(
                surahs = surahMap, ayahs = ayahList, number = pageNumber,
                singleSurah = surahs.size == 1,
                juz = juz,
            )
            ayahByPageCache.put(pageNumber, page)
            emit(Success(page))
        }.onStart {
            val page = ayahByPageCache.get(pageNumber)
            if (page == null) emit(Loading()) else emit(Success(page))
        }.catch { e ->
            Log.e(TAG, "ayahByPage: Failed ($pageNumber), cause, ${e.message}", e)
            emit(Error())
        }.flowOn(Dispatchers.IO)
    }

}

private const val TAG = "MushafRepositoryImpl"