package com.example.movies.model

import android.os.Parcel
import android.os.Parcelable

data class Images(
    var base_url: String?,
    var secure_base_url: String?,
    var backdrop_sizes: List<String>?,
    var logo_sizes: List<String>?,
    var poster_sizes: List<String>?,
    var profile_sizes: List<String>?,
    var still_sizes: List<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(base_url)
        parcel.writeString(secure_base_url)
        parcel.writeStringList(backdrop_sizes)
        parcel.writeStringList(logo_sizes)
        parcel.writeStringList(poster_sizes)
        parcel.writeStringList(profile_sizes)
        parcel.writeStringList(still_sizes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Images> {
        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }
}