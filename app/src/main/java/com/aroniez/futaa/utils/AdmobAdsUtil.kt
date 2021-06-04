package com.aroniez.futaa.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.*
import com.aroniez.futaa.AppExecutors
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.include_google_native_ad.view.*


object AdmobAdsUtil {

    private const val testAdmobAdId = "ca-app-pub-1828433523816340~4435694956"
    private const val testAdmobBannerId = "ca-app-pub-1828433523816340/9304878251"
    private const val testAdmobInterstialId = "ca-app-pub-1828433523816340/9113306567"
    private const val testAdmobNativeId = "ca-app-pub-1828433523816340/4982489861"

    private val tag = AdmobAdsUtil::class.java.simpleName

    fun loadRewardedVideoAd(context: Context) {
        val rewardedAd = MobileAds.getRewardedVideoAdInstance(context)
        rewardedAd.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewardedVideoAdClosed() {}

            override fun onRewardedVideoAdLeftApplication() {}

            override fun onRewardedVideoAdLoaded() {
                rewardedAd.show()
            }

            override fun onRewardedVideoAdOpened() {}

            override fun onRewardedVideoCompleted() {}

            override fun onRewarded(p0: RewardItem?) {}

            override fun onRewardedVideoStarted() {}

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {}
        }

        val request = AdRequest.Builder()
                //.addTestDevice("C3867D031E24500617388958146230B2")
                .build()
        rewardedAd.loadAd("ca-app-pub-1955254598121537/7677884088", request)

    }

    fun loadInterstialAds(context: Context) {
        val mInterstitialAd = InterstitialAd(context)
        when ((1..2).random()) {
            1 -> mInterstitialAd.adUnitId = admobInterstialId
            else -> mInterstitialAd.adUnitId = testAdmobInterstialId
        }

        val request = AdRequest.Builder()
                .addTestDevice("24E29ED25F20C74BC7E5A3924B408BBF")
                .build()
        mInterstitialAd.loadAd(request)

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d(tag, "onAdLoaded.")
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.d(tag, "onAdFailedToLoad: $errorCode")
            }

            override fun onAdOpened() {
                Log.d(tag, "onAdOpened:")
            }

            override fun onAdClicked() {
                Log.d(tag, "onAdClicked:")
            }

            override fun onAdLeftApplication() {
                Log.d(tag, "onAdLeftApplication:")
            }

            override fun onAdClosed() {
                Log.d(tag, "onAdClosed:")
            }
        }

    }

    fun loadBannerAds(context: Context, linearLayout: LinearLayout) {
        val adThread = object : Thread() {
            override fun run() {
                val request = AdRequest.Builder()
                        .addTestDevice("24E29ED25F20C74BC7E5A3924B408BBF")
                        .build()
                AppExecutors().mainThread().execute {
                    val googleAdView = AdView(context)
                    googleAdView.adSize = AdSize.BANNER
                    when ((1..2).random()) {
                        1 -> googleAdView.adUnitId = admobBannerId
                        else -> googleAdView.adUnitId = testAdmobBannerId
                    }

                    googleAdView.loadAd(request)
                    linearLayout.addView(googleAdView)
                }
            }
        }
        adThread.start()
    }

    fun loadNativeAds(context: Context, linearLayout: LinearLayout) {
        val request = AdRequest.Builder()
                .addTestDevice("24E29ED25F20C74BC7E5A3924B408BBF")
                .build()

        val googleAdView = AdView(context)
        googleAdView.adSize = AdSize.MEDIUM_RECTANGLE
        when ((1..2).random()) {
            1 -> googleAdView.adUnitId = admobBannerId
            else -> googleAdView.adUnitId = testAdmobBannerId
        }
        googleAdView.loadAd(request)
        linearLayout.removeAllViews()
        linearLayout.addView(googleAdView)
    }

    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        adView.mediaView = adView.ad_media

        // Set other ad assets.
        adView.headlineView = adView.findViewById(com.aroniez.futaa.R.id.ad_headline)
        adView.bodyView = adView.findViewById(com.aroniez.futaa.R.id.ad_body)
        adView.callToActionView = adView.findViewById(com.aroniez.futaa.R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(com.aroniez.futaa.R.id.ad_app_icon)
        adView.priceView = adView.findViewById(com.aroniez.futaa.R.id.ad_price)
        adView.starRatingView = adView.findViewById(com.aroniez.futaa.R.id.ad_stars)
        adView.storeView = adView.findViewById(com.aroniez.futaa.R.id.ad_store)
        adView.advertiserView = adView.findViewById(com.aroniez.futaa.R.id.ad_advertiser)

        (adView.headlineView as TextView).text = nativeAd.headline

        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        adView.setNativeAd(nativeAd)
        val vc = nativeAd.videoController
        if (vc.hasVideoContent()) {
            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
            }
        } else {

        }
    }
}