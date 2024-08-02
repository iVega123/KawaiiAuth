package com.auth.kawaii.controller.article

import com.auth.kawaii.model.Article
import com.auth.kawaii.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article")
class ArticleController (
    private val articleService: ArticleService
){

    @GetMapping
    fun ListAll(): List<ArticleResponse> =
        articleService.findAll()
            .map { it.toResponse() }
    private fun Article.toResponse() : ArticleResponse =
        ArticleResponse(
            id= this.id,
            title = this.title,
            content = this.content
        )



}