/*
 * Copyright (c) 2020. By Shahin Montazeri (shahin.montazeri@gmail.com)
 * Programmed for demonstration purposes
 */

package com.example.myapplication.data.services

import androidx.annotation.Keep

@Keep
sealed class ServiceType {
    object Artists: ServiceType()
    object Albums: ServiceType()
    object Tracks: ServiceType()
}