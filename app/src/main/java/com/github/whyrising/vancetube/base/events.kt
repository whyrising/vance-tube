package com.github.whyrising.vancetube.base

import com.github.whyrising.recompose.fx.FxIds
import com.github.whyrising.recompose.regEventDb
import com.github.whyrising.recompose.regEventFx
import com.github.whyrising.vancetube.base.base.is_top_bar_fixed
import com.github.whyrising.vancetube.base.base.set_backstack_status
import com.github.whyrising.y.core.collections.IPersistentMap
import com.github.whyrising.y.core.m
import com.github.whyrising.y.core.v

typealias AppDb = IPersistentMap<Any, Any>

fun regBaseEventHandlers() {
  regEventFx(base.navigate) { _, (_, destination) ->
    m(FxIds.fx to v(v(base.navigate, (destination as Enum<*>).name)))
  }

  regEventDb<AppDb>(set_backstack_status) { db, (_, flag) ->
    db.assoc(base.is_backstack_available, flag)
  }

  regEventDb<AppDb>(is_top_bar_fixed) { db, (_, canBeScrolled) ->
    db.assoc(is_top_bar_fixed, !(canBeScrolled as Boolean))
  }
}
