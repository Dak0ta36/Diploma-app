package com.example.diploma2

enum class Rtypes(val rt: Byte) {
    Startscan (1),
    Stopscan(2),
    Calibrate(3),
    SetFreq(4),
    Download(5),
}