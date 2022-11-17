package com.example.historyvideokotlin.repository

import androidx.lifecycle.LiveData
import com.example.historyvideokotlin.data.KeywordDao
import com.example.historyvideokotlin.model.Keyword

class KeywordRepository(val keywordDao: KeywordDao) {
    val videoKeyword: LiveData<List<Keyword>> = keywordDao.getVideoKeyword()

    val postKeyword: LiveData<List<Keyword>> = keywordDao.getPostKeyword()

    suspend fun insertKeyword(keyword: Keyword) {
        keywordDao.insertKeyword(keyword)
    }

    suspend fun updateKeyword(keyword: Keyword) {
        keywordDao.updateKeyword(keyword)
    }

    suspend fun deleteKeyword(keyword: Keyword) {
        keywordDao.deleteKeyword(keyword)
    }

    suspend fun deleteAllKeywords() {
        keywordDao.deleteAllKeywords()
    }
}