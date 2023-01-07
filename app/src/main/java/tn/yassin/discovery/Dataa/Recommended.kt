package tn.yassin.discovery.data


const val Nom = "NAME"
const val Image = "Image"
const val Place = "Place"
const val Rating = "Rating"


data class Recommended(
    val Image: Int,
    val Nom: String,
    val Place: String,
    val Rating: Int

 )
