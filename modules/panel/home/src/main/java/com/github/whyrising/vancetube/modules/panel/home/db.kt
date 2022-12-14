package com.github.whyrising.vancetube.modules.panel.home

import com.github.whyrising.recompose.cofx.regCofx
import com.github.whyrising.recompose.events.Event
import com.github.whyrising.recompose.ids.coeffects
import com.github.whyrising.recompose.ids.recompose
import com.github.whyrising.vancetube.modules.core.keywords.home
import com.github.whyrising.vancetube.modules.panel.common.States
import com.github.whyrising.vancetube.modules.panel.common.appDbBy
import com.github.whyrising.y.core.get

// -- Spec ---------------------------------------------------------------------

/**
 * {:state [States.Loading]
 * :home/popular_vids (
 * [com.github.whyrising.vancetube.modules.panel.common.VideoData])
 * }
 */

// -- Cofx Registrations -------------------------------------------------------

val regHomeCofx = regCofx(home.fsm) { cofx ->
  val (eventId) = cofx[coeffects.originalEvent] as Event
  val nextDb = updateToNextState(appDbBy(cofx), eventId)
  cofx.assoc(recompose.db, nextDb)
}
