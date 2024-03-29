package com.example.myschool.navigasi

import DestinasiDashboard
import HalamanDashboard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myschool.R
import com.example.myschool.ui.halaman.DestinasiEntry
import com.example.myschool.ui.halaman.DestinasiHome
import com.example.myschool.ui.halaman.DestinasiHomeSiswa
import com.example.myschool.ui.halaman.DetailsDestination
import com.example.myschool.ui.halaman.DetailsScreen
import com.example.myschool.ui.halaman.EntrySiswaScreen
import com.example.myschool.ui.halaman.HomeScreen
import com.example.myschool.ui.halaman.HomeScreenSiswa
import com.example.myschool.ui.halaman.ItemEditDestination
import com.example.myschool.ui.halaman.ItemEditScreen

@Composable
fun JadwalSiswaApp(navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController)
}

// Fungsi SiswaTopAppBar digunakan untuk menampilkan app bar pada aplikasi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        })
}
// Fungsi HostNavigasi berfungsi sebagai navigasi antar halaman
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Mendefinisikan NavHost untuk menangani navigasi menggunakan Compose Navigation
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = Modifier
    ){
        //Menetapkan fungsi composable untuk halaman Dashboard
        composable(DestinasiDashboard.route) {
            HalamanDashboard(
                onNextButtonClicked1 = {
                navController.navigate(DestinasiHomeSiswa.route)
            },
                onNextButtonClicked2 = {
                    navController.navigate(DestinasiHome.route)
                } )
        }
        // Menetapkan fungsi composable untuk halaman Siswa
        composable(DestinasiHomeSiswa.route) {
            HomeScreenSiswa(
                navigateBack ={ navController.navigate(DestinasiDashboard.route)},
            )
        }
        // Menetapkan fungsi composable untuk halaman home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateBack ={ navController.navigate(DestinasiDashboard.route)},
                onDetailClick = { itemId ->
                    navController.navigate("${DetailsDestination.route}/$itemId")
                },
            )
        }
        // Menetapkan fungsi composable untuk halaman entry siswa
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = { navController.popBackStack() })
        }
        // Menetapkan fungsi composable untuk halaman detail jadwal siswa
        composable(
            DetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestination.siswaIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt(DetailsDestination.siswaIdArg)
            itemId?.let {
                DetailsScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") }
                )
            }
        }
        // Menetapkan fungsi composable untuk halaman edit jadwal siswa
        composable(
            ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
