package com.plugins

import io.ktor.server.application.*
import io.ktor.server.thymeleaf.Thymeleaf
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix            = "templates/"
            suffix            = ".html"
            characterEncoding = "utf-8"
        })
    }
}
