package com.example.demo.application.spring.controller

import com.example.demo.business.Interactor
import com.example.demo.ui.CreateAccountRequest
import com.example.demo.ui.CreateAccountResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/accounts")
class AccountController {

    final Interactor interactor

    AccountController(final Interactor interactor) {
        this.interactor = interactor
    }

    @PostMapping
    CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        return interactor.createAccount(request)
    }
}
