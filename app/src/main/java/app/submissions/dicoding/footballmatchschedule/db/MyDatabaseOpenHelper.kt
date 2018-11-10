package app.submissions.dicoding.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import app.submissions.dicoding.footballmatchschedule.db.tables.Favorites
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, VERSION) {

  override fun onCreate(db: SQLiteDatabase?) {
    Log.i("tables", "creating tables")
    db?.createTable(Favorites.TABLE_NAME, true,
        Favorites.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Favorites.DATA to TEXT,
        Favorites.TYPE to INTEGER,
        Favorites.CLASS_NAME to TEXT)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    Log.i("tables", "dropping tables")
    db?.dropTable(Favorites.TABLE_NAME, true)
  }

  companion object {
    const val DB_NAME = "MatchNews.db"
    const val VERSION = 2

    @Synchronized
    fun getInstance(ctx: Context): MyDatabaseOpenHelper {
      return MyDatabaseOpenHelper(ctx)
    }
  }

}