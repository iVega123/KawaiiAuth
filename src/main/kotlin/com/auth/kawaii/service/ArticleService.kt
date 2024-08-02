package com.auth.kawaii.service

import com.auth.kawaii.model.Article
import com.auth.kawaii.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService (
    private val articleRepository: ArticleRepository
){
    fun findAll(): List<Article> =
             articleRepository.findAll()
}