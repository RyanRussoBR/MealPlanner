package br.pucpr.mealplanner.di

import android.content.Context
import androidx.room.Room
import br.pucpr.mealplanner.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "meal_planner.db").build()

    @Provides fun provideRecipeDao(db: AppDatabase) = db.recipeDao()
    @Provides fun providePlannedMealDao(db: AppDatabase) = db.plannedMealDao()
    @Provides fun provideShoppingDao(db: AppDatabase) = db.shoppingDao()
}
