package com.github.whyrising.vancetube.base

import com.github.whyrising.recompose.dispatchSync
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.vancetube.modules.core.keywords.base
import com.github.whyrising.vancetube.modules.core.keywords.base.init_app_db
import com.github.whyrising.vancetube.modules.core.keywords.home
import com.github.whyrising.y.core.m
import com.github.whyrising.y.core.v

// TODO: Move this!
// const val DEFAULT_BASE_ADDRESS = "invidious.namazso.eu" // empty
// const val DEFAULT_BASE_ADDRESS = "invidious.snopyta.org" // 403
// const val DEFAULT_BASE_ADDRESS = "invidious.tiekoetter.com"
const val DEFAULT_BASE_ADDRESS = "youtube.076.ne.jp"
// const val DEFAULT_BASE_ADDRESS = "y.com.sb"
// const val DEFAULT_BASE_ADDRESS = "invidious.slipfox.xyz"

val defaultDb = m<base, Any>(
  base.is_backstack_available to false,
  base.api to "https://$DEFAULT_BASE_ADDRESS/api/v1",
  base.start_route to home.route.toString()
)

fun initAppDb() {
  regEventDb<AppDb>(init_app_db) { _, _ ->
    com.github.whyrising.vancetube.modules.panel.home.updateToNextState(
      db = defaultDb,
      event = home.load_popular_videos
    )
  }
  dispatchSync(v(init_app_db))
}