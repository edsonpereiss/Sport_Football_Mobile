package com.aroniez.futaa.ui.home

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aroniez.futaa.R
import com.aroniez.futaa.api.selectedLang
import com.aroniez.futaa.services.MatchesReloadService
import com.aroniez.futaa.ui.about.AboutActivity
import com.aroniez.futaa.ui.competitions.LeaguesHomeFragment
import com.aroniez.futaa.ui.competitions.leagues.Competitions1Fragment
import com.aroniez.futaa.ui.favorites.FavoriteMatchesFragment
import com.aroniez.futaa.ui.livegames.LiveMatchesFragment
import com.aroniez.futaa.ui.matches.fragments.MatchesHomeFragment
import com.aroniez.futaa.utils.AdmobAdsUtil
import com.aroniez.futaa.utils.loadBannerAds
import com.aroniez.futaa.utils.loadInterstialAds
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() {
    private val bottomBarDashboardPosition = 4
    private val bottomBarPaybillPosition = 1
    private val bottomBarDepositPosition = 3
    private val bottomBarStudentsPosition = 2

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        // Obtain the FirebaseAnalytics instance.
        //StartAppSDK.init(this, "211858772", false)
        //StartAppAd.disableSplash()
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        MobileAds.initialize(this, "ca-app-pub-1955254598121537~7845093982")

        changeLocalLanguage()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = ""
        }


        handleBottomBarTabClicks()
        bottomBar.selectedItemId = R.id.action_live_matches

        startService(Intent(this, MatchesReloadService::class.java))

        loadBannerAds(this, homeBannerLayout)
        loadInterstialAds(this)
        AdmobAdsUtil.loadRewardedVideoAd(this)

    }

    private fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("Facebook", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("Facebook", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("Facebook", "printHashKey()", e)
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun changeLocalLanguage() {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(selectedLang)) // API 17+ only.
        res.updateConfiguration(conf, dm)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == R.id.action_about_us) {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
        if (id == R.id.action_share) {

        }
        if (id == R.id.action_rate) {

        }
        return super.onOptionsItemSelected(item)
    }


    private fun handleBottomBarTabClicks() {
        bottomBar!!.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (id == R.id.action_live_matches) {
                replaceBottomBarFragments(bottomBarDashboardPosition)
            }
            if (id == R.id.action_matches) {
                replaceBottomBarFragments(bottomBarPaybillPosition)
            }
            if (id == R.id.action_teams) {
                replaceBottomBarFragments(bottomBarDepositPosition)
            }
            if (id == R.id.action_news) {
                replaceBottomBarFragments(bottomBarStudentsPosition)
            }
            true
        }
    }

    private fun replaceBottomBarFragments(position: Int) {
        val manager = supportFragmentManager

        val fragmentOne = manager.findFragmentByTag("one")
        val fragmentTwo = manager.findFragmentByTag("two")
        val fragmentThree = manager.findFragmentByTag("three")
        val fragmentFour = manager.findFragmentByTag("four")

        if (position == bottomBarDashboardPosition) {
            toolbarTitle.text = getString(R.string.upcoming_matches_title)
            if (fragmentOne != null) {
                manager.beginTransaction().show(fragmentOne).commit()
            } else {
                manager.beginTransaction().add(R.id.contentContainer, MatchesHomeFragment(), "one").commit()
            }

            if (fragmentTwo != null) {
                manager.beginTransaction().hide(fragmentTwo).commit()
            }
            if (fragmentThree != null) {
                manager.beginTransaction().hide(fragmentThree).commit()
            }
            if (fragmentFour != null) {
                manager.beginTransaction().hide(fragmentFour).commit()
            }

        }
        if (position == bottomBarPaybillPosition) {
            toolbarTitle.text = getString(R.string.live_matches_title)

            if (fragmentTwo != null) {
                manager.beginTransaction().show(fragmentTwo).commit()
            } else {
                manager.beginTransaction().add(R.id.contentContainer, LiveMatchesFragment(), "two").commit()
            }
            //we hide all other visible fragments
            if (fragmentOne != null) {
                manager.beginTransaction().hide(fragmentOne).commit()
            }
            if (fragmentThree != null) {
                manager.beginTransaction().hide(fragmentThree).commit()
            }
            if (fragmentFour != null) {
                manager.beginTransaction().hide(fragmentFour).commit()
            }
        }
        if (position == bottomBarDepositPosition) {
            toolbarTitle.text = getString(R.string.favorite_matches_title)

            if (fragmentThree != null) {
                manager.beginTransaction().show(fragmentThree).commit()
                //FavoriteMatchesFragment().loadCompetitions()
            } else {
                manager.beginTransaction().add(R.id.contentContainer, FavoriteMatchesFragment(), "three").commit()
            }
            //we hide all other visible fragments
            if (fragmentOne != null) {
                manager.beginTransaction().hide(fragmentOne).commit()
            }
            if (fragmentTwo != null) {
                manager.beginTransaction().hide(fragmentTwo).commit()
            }
            if (fragmentFour != null) {
                manager.beginTransaction().hide(fragmentFour).commit()
            }
        }
        if (position == bottomBarStudentsPosition) {
            toolbarTitle.text = getString(R.string.soccer_league_title)
            if (manager.findFragmentByTag("four") != null) {
                manager.beginTransaction().show(manager.findFragmentByTag("four")!!).commit()
            } else {
                manager.beginTransaction().add(R.id.contentContainer, LeaguesHomeFragment(), "four").commit()
            }
            //we hide all other visible fragments
            if (fragmentTwo != null) {
                manager.beginTransaction().hide(fragmentTwo).commit()
            }
            if (fragmentThree != null) {
                manager.beginTransaction().hide(fragmentThree).commit()
            }
            if (fragmentOne != null) {
                manager.beginTransaction().hide(fragmentOne).commit()
            }
        }
    }
}
