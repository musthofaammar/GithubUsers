package id.eureka.githubusers.users.presentation.model

import androidx.paging.PagingData

sealed interface UserUIState{
    object Loading : UserUIState
    data class Error(val message : String) : UserUIState
    data class SearchUserSuccess(val data : PagingData<User>) : UserUIState
    object Empty : UserUIState
}