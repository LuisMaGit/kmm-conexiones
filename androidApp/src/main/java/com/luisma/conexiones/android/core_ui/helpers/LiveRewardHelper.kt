package com.luisma.conexiones.android.core_ui.helpers

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.luisma.conexiones.android.R

object LiveRewardHelper {
    private var rewardedAd: RewardedAd? = null
    fun loadRewarded(context: Context, onLoaded: () -> Unit) {
        if (rewardedAd != null) return

        RewardedAd.load(context,
            context.resources.getString(R.string.ad_mob_live_reward_unit_id),
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    rewardedAd = null
                }

                override fun onAdLoaded(rewardedAd1: RewardedAd) {
                    super.onAdLoaded(rewardedAd1)
                    rewardedAd = rewardedAd1
                    onLoaded()
                }

            }
        )
    }

    fun showRewardedAdd(context: Context, onDismissed: () -> Unit) {
        if (rewardedAd != null) {
            rewardedAd?.show(
                context as Activity, OnUserEarnedRewardListener {
                    onDismissed()
                    rewardedAd = null
                }
            )
        }
    }

    fun removeRewarded() {
        rewardedAd = null
    }
}