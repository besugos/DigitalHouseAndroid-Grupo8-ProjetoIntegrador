package com.besugos.marveluniverse

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.besugos.marveluniverse.home.view.CollectionFragment
import com.besugos.marveluniverse.login.view.LoginActivity

const val TAG_COLLECTION_FRAGMENT = "MAIN"
const val INDEX = "INDEX"

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mainToolbar: androidx.appcompat.widget.Toolbar
    private lateinit var btnLogout: Button
    private var collectionFragment = CollectionFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        mainToolbar = findViewById(R.id.mainToolbar)
        btnLogout = findViewById(R.id.btnLogout)

        setSupportActionBar(mainToolbar)
        initializeDrawer()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, collectionFragment, TAG_COLLECTION_FRAGMENT).commit()

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        initializeButtonListenerToSetCollectionTab(R.id.btnNavigateToHeroes, 0)
        initializeButtonListenerToSetCollectionTab(R.id.btnNavigateToEvents, 1)
        initializeButtonListenerToSetCollectionTab(R.id.btnNavigateToComics, 2)
        initializeButtonListenerToSetCollectionTab(R.id.btnNavigateToFavs, 3)

    }

    private fun initializeButtonListenerToSetCollectionTab(buttonId: Int, tabPosition: Int) {
        findViewById<Button>(buttonId).setOnClickListener {
            collectionFragment.setCollectionTab(tabPosition)

            val homeFragment = supportFragmentManager.findFragmentByTag(TAG_COLLECTION_FRAGMENT)

            if (homeFragment != null && homeFragment.isVisible) {
                collectionFragment.setCollectionTab(tabPosition)
            } else {

                val args = Bundle()
                args.putInt(INDEX, tabPosition)

                collectionFragment = CollectionFragment()
                collectionFragment.arguments = args

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, collectionFragment, TAG_COLLECTION_FRAGMENT).commit()

            }

            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun initializeDrawer() {

        val toggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}