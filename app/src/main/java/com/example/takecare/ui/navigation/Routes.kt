package com.example.takecare.ui.navigation

import com.example.takecare.R

sealed class Routes(val route: String, string: String, icMenuSend: Int) {
    object Login : Routes("login", "Inicio", android.R.drawable.ic_menu_send)
    object Register : Routes("register", "Inicio", android.R.drawable.ic_menu_send)
    object Home : Routes("home", "Inicio", android.R.drawable.ic_menu_send)
    object Loading : Routes("loading", "Cargando", android.R.drawable.ic_menu_send)
}

sealed class HomeRoutes(val route: String, val label: String, val icon: Int) {
    object Home : HomeRoutes("home", "Inicio", R.drawable.home)
    object Messages : HomeRoutes("messages", "Mensajes", R.drawable.message)
    object Diary : HomeRoutes("diary", "Mi Diario", R.drawable.book)
    object Profile : HomeRoutes("profile", "Perfil", R.drawable.profile)
    object CreatePost : HomeRoutes("createPost", "Crear Post", android.R.drawable.btn_plus)
    object OpenPost : HomeRoutes("openPost", "Post Abierto", android.R.drawable.btn_plus)
    object PsycoList : HomeRoutes("psycoList", "Lista de psicologos", android.R.drawable.btn_plus)
}