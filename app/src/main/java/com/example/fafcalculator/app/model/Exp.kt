package com.example.fafcalculator.app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.fafcalculator.R

enum class Exp(
    @DrawableRes val iconResId: Int,
    @StringRes val coastResId: Int,
    @StringRes val titleResId: Int,
    @StringRes val factionColorResId: Int
) {
    COAST_250200(R.drawable.paragon, R.string.paragon_coast, R.string.paragon, R.color.green_aeon),
    COAST_202500(R.drawable.salvation, R.string.salvation_coast, R.string.salvation, R.color.green_aeon),
    COAST_73200(R.drawable.emissary, R.string.emissary_coast, R.string.emissary, R.color.green_aeon),
    COAST_45500(R.drawable.czar, R.string.czar_coast, R.string.czar, R.color.green_aeon),
    COAST_27500(R.drawable.colossus, R.string.colossus_coast, R.string.colossus, R.color.green_aeon),
    COAST_22000(R.drawable.tempest, R.string.tempest_coast, R.string.tempest, R.color.green_aeon),

    COAST_224775(R.drawable.mavor, R.string.mavor_coast, R.string.mavor, R.color.blue_uef),
    COAST_72000(R.drawable.duke, R.string.duke_coast, R.string.duke, R.color.blue_uef),
    COAST_36000(R.drawable.novax, R.string.novax_coast, R.string.novax, R.color.blue_uef),
    COAST_28000(R.drawable.fatboy, R.string.fatboy_coast, R.string.fatboy, R.color.blue_uef),
    COAST_12000(R.drawable.atlantis, R.string.atlantis_coast, R.string.atlantis, R.color.blue_uef),

    COAST_220000(R.drawable.scathis, R.string.scathis_coast, R.string.scathis, R.color.red_cybran),
    COAST_69600(R.drawable.disruptor, R.string.disruptor_coast, R.string.disruptor, R.color.red_cybran),
    COAST_37500(R.drawable.megalith, R.string.megalith_coast, R.string.megalith, R.color.red_cybran),
    COAST_34000(R.drawable.soulripper, R.string.soulripper_coast, R.string.soulripper, R.color.red_cybran),
    COAST_20000(R.drawable.monkeylord, R.string.monkeylord_coast, R.string.monkeylord, R.color.red_cybran),

    COAST_187650(R.drawable.yolonaoss, R.string.yolonaoss_coast, R.string.yolonaoss, R.color.yellow_seraphim),
    COAST_78000(R.drawable.hovatham, R.string.hovatham_coast, R.string.hovatham, R.color.yellow_seraphim),
    COAST_48000(R.drawable.ahwassa, R.string.ahwassa_coast, R.string.ahwassa, R.color.yellow_seraphim),
    COAST_26500(R.drawable.ythotha, R.string.ythotha_coast, R.string.ythotha, R.color.yellow_seraphim);

    companion object {
        fun findImageByCoast(coast: Int): Int {
            for (exp in Exp.values()) {
                if (exp.toString().equals("COAST_$coast", true))
                    return exp.iconResId
            }
            return R.drawable.mass
        }

        fun getList(): List<ExpEntity> {
            val list = mutableListOf<ExpEntity>()
            enumValues<Exp>().forEach {
                list.add(
                    ExpEntity(
                        it.iconResId,
                        it.coastResId,
                        it.titleResId,
                        it.factionColorResId
                    )
                )
            }
            return list
        }
    }
}