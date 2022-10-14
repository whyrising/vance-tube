package com.github.whyrising.vancetube.modules.panel.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import com.github.whyrising.recompose.dispatch
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.recompose.w
import com.github.whyrising.vancetube.modules.core.keywords.common
import com.github.whyrising.vancetube.modules.core.keywords.home
import com.github.whyrising.vancetube.modules.designsystem.component.VideosGrid
import com.github.whyrising.vancetube.modules.designsystem.component.VideosList
import com.github.whyrising.vancetube.modules.designsystem.component.VideosPanel
import com.github.whyrising.vancetube.modules.designsystem.data.Videos
import com.github.whyrising.vancetube.modules.designsystem.data.VideosPanelState
import com.github.whyrising.vancetube.modules.designsystem.theme.enterAnimation
import com.github.whyrising.vancetube.modules.designsystem.theme.exitAnimation
import com.github.whyrising.y.core.v
import com.google.accompanist.navigation.animation.composable

// -- navigation ---------------------------------------------------------------

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.homeCommon(
  animOffSetX: Int,
  content: @Composable (videos: Videos) -> Unit
) {
  composable(
    route = home.route.toString(),
    exitTransition = { exitAnimation(targetOffsetX = -animOffSetX) },
    popEnterTransition = { enterAnimation(initialOffsetX = -animOffSetX) }
  ) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
      regHomeEvents(scope)
      dispatch(v(home.load_popular_videos))
    }
    regHomeSubs

    VideosPanel(
      state = subscribe<VideosPanelState>(
        qvec = v(home.view_model, stringResource(R.string.views_label))
      ).w(),
      onRefresh = { dispatch(v(home.refresh)) },
      content = content
    )
  }
}

fun NavGraphBuilder.home(animOffSetX: Int, orientation: Int) {
  homeCommon(animOffSetX) { videos ->
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
      dispatch(v(common.expand_top_app_bar))
      regScrollToTopListFx(scope) {
        listState.animateScrollToItem(0)
      }
    }

    VideosList(
      orientation = orientation,
      listState = listState,
      videos = videos
    )
  }
}

fun NavGraphBuilder.homeLarge(animOffSetX: Int, orientation: Int) {
  homeCommon(animOffSetX) { videos ->
    val scope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    LaunchedEffect(Unit) {
      regScrollToTopListFx(scope) {
        gridState.animateScrollToItem(0)
      }
    }
    VideosGrid(
      orientation = orientation,
      gridState = gridState,
      videos = videos
    )
  }
}
