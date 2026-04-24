package com.codepath.charactercreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {

                composable("home") {
                    HomeScreen(
                        onBeginClicked = { navController.navigate("class") }
                    )
                }

                composable("class") {
                    ClassScreen(
                        onClassSelected = { characterClass ->
                            navController.navigate("stat/$characterClass")
                        }
                    )
                }

                composable("stat/{characterClass}") { backStackEntry ->
                    val characterClass = backStackEntry.arguments?.getString("characterClass") ?: ""
                    StatScreen(
                        characterClass = characterClass,
                        onStatSelected = { stat ->
                            navController.navigate("weapon/$characterClass/$stat")
                        }
                    )
                }

                composable("weapon/{characterClass}/{stat}") { backStackEntry ->
                    val characterClass = backStackEntry.arguments?.getString("characterClass") ?: ""
                    val stat          = backStackEntry.arguments?.getString("stat")            ?: ""
                    WeaponScreen(
                        characterClass = characterClass,
                        stat = stat,
                        // STARTER BUG 2 GOES HERE:
                        // Change navigate("ability/$characterClass/$stat/$weapon")
                        //      to navigate("ability/$characterClass/$weapon/$stat")  ← stat and weapon swapped
                        onWeaponSelected = { weapon ->
                            navController.navigate("ability/$characterClass/$stat/$weapon")
                        }
                    )
                }

                composable("ability/{characterClass}/{stat}/{weapon}") { backStackEntry ->
                    val characterClass = backStackEntry.arguments?.getString("characterClass") ?: ""
                    val stat           = backStackEntry.arguments?.getString("stat")           ?: ""
                    val weapon         = backStackEntry.arguments?.getString("weapon")         ?: ""
                    AbilityScreen(
                        characterClass = characterClass,
                        stat = stat,
                        weapon = weapon,
                        onAbilitySelected = { ability ->
                            navController.navigate("card/$characterClass/$stat/$weapon/$ability")
                        }
                    )
                }

                composable("card/{characterClass}/{stat}/{weapon}/{ability}") { backStackEntry ->
                    val characterClass = backStackEntry.arguments?.getString("characterClass") ?: ""
                    val stat           = backStackEntry.arguments?.getString("stat")           ?: ""
                    val weapon         = backStackEntry.arguments?.getString("weapon")         ?: ""
                    val ability        = backStackEntry.arguments?.getString("ability")        ?: ""
                    CharacterCardScreen(
                        characterClass = characterClass,
                        stat = stat,
                        weapon = weapon,
                        ability = ability,
                        onStartOver = {
                            navController.popBackStack("home", inclusive = false)
                        }
                    )
                }
            }
        }
    }
}
