package com.temlete.domain.entity

data class RandomUser(var info: Info,
                      var results: List<Result>)

data class Info(var seed: String,
                var results: Int,
                var page: Int,
                var version: String)

data class Result(var gender: String,
                  var email: String,
                  var name: Name,
                  var phone: String,
                  var cell: String)

data class Name(var first: String,
                var last: String,
                var title: String)