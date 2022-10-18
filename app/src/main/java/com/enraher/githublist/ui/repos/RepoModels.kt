package com.enraher.githublist.ui.repos


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

class RepoModels {

    @Entity(tableName = RepoEntity.TABLE_NAME)
    data class RepoEntity(
        @ColumnInfo(name = COLUMN_NAME_ID)
        @PrimaryKey
        val id: String,

        @ColumnInfo(name = COLUMN_NAME_URL)
        val url: String,

        @ColumnInfo(name = COLUMN_NAME_DISPLAY_NAME)
        val name: String,

        @ColumnInfo(name = COLUMN_NAME_DESCRIPTION)
        val description: String?,

        @ColumnInfo(name = COLUMN_NAME_FORK)
        val fork: Boolean,

        @ColumnInfo(name = COLUMN_NAME_OWNER_LOGIN)
        val ownerLogin: String,

        @ColumnInfo(name = COLUMN_NAME_OWNER_AVATAR)
        val ownerAvatar: String,

        @ColumnInfo(name = COLUMN_NAME_FULL_NAME)
        val fullName: String,

        @ColumnInfo(name = COLUMN_NAME_OPEN_ISSUES)
        val openIssues: String,

        @ColumnInfo(name = COLUMN_NAME_FORKS)
        val forks: Int,

        @ColumnInfo(name = COLUMN_NAME_WATCHERS)
        val watchers: Int,

        @ColumnInfo(name = COLUMN_NAME_STORED_AT)
        val storedAt: Long
    ){
        companion object {
            const val TABLE_NAME = "repos"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_URL = "url"
            const val COLUMN_NAME_DISPLAY_NAME = "name"
            const val COLUMN_NAME_DESCRIPTION = "description"
            const val COLUMN_NAME_FORK = "fork"
            const val COLUMN_NAME_OWNER_LOGIN = "ownerLogin"
            const val COLUMN_NAME_OWNER_AVATAR = "ownerAvatar"
            const val COLUMN_NAME_FULL_NAME = "fullName"
            const val COLUMN_NAME_OPEN_ISSUES = "openIssues"
            const val COLUMN_NAME_FORKS = "forks"
            const val COLUMN_NAME_WATCHERS = "watchers"
            const val COLUMN_NAME_STORED_AT = "storedAt"
        }
    }

    data class Repo(
        val id: String,
        val url: String,
        val name: String,
        val description: String?,
        val fork: Boolean,
        val ownerLogin: String,
        val ownerAvatar: String,
        val fullName: String,
        val openIssues: String,
        val forks: Int,
        val watchers: Int
    ) {
        companion object {
            @JvmStatic
            @BindingAdapter("ownerAvatar")
            fun loadImage(view: ImageView, profileImage: String?) {
                Glide.with(view.context)
                    .load(profileImage)
                    .into(view)
            }
        }
    }

    data class RepoResponse(
        val id: String,
        val url: String,
        val name: String,
        val description: String?,
        val fork: Boolean,
        val owner: OwnerResponse,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("open_issues")
        val openIssues: String,
        val forks: Int,
        val watchers: Int
    )

    data class OwnerResponse(
        val id: Long,
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String)


}
