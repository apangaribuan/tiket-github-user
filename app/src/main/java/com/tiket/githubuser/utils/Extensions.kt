package com.tiket.githubuser.utils

import com.google.gson.Gson


/* ============================================
	Created by andy pangaribuan on 2020/07/26
	Copyright BoltIdea. All rights reserved.
   ============================================ */
inline fun <reified T: JSONConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)
