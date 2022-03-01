package modulon.layout.recycler

import android.graphics.Canvas
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import modulon.extensions.stdlib.logcatUI
import kotlin.math.abs

/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.5f

/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

class BounceEdgeEffectFactory(private val parentDirection: Int) : RecyclerView.EdgeEffectFactory() {

    override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {

        return object : EdgeEffect(recyclerView.context) {

            // A reference to the [SpringAnimation] for this RecyclerView used to bring the item back after the over-scroll effect.
            var translationAnim: SpringAnimation? = null

            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            private fun handlePull(deltaDistance: Float) {
                // This is called on every touch event while the list is scrolled with a finger.

                // Translate the recyclerView with the distance
                if (parentDirection == RecyclerView.VERTICAL) {
                    val sign = if (direction == DIRECTION_BOTTOM) -1 else if (direction == DIRECTION_TOP) 1  else return
                    val percent = if (recyclerView.height == 0) 0f else 1 - abs(recyclerView.translationY / recyclerView.height).coerceIn(0f..1f)
                    val translationYDelta = percent * percent * sign * recyclerView.height * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    recyclerView.translationY += translationYDelta
                } else {
                    if (recyclerView.width == 0) return
                    val sign = if (direction == DIRECTION_RIGHT) -1 else if (direction == DIRECTION_LEFT) 1 else return
                    val percent = if (recyclerView.width == 0) 0f else 1 - abs(recyclerView.translationX / recyclerView.width).coerceIn(0f..1f)
                    val translationYDelta = percent * percent * sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    recyclerView.translationX += translationYDelta
                }

                translationAnim?.cancel()
            }

            override fun onRelease() {
                super.onRelease()
                // The finger is lifted. Start the animation to bring translation back to the resting state.

                if (parentDirection == RecyclerView.VERTICAL) {
                    if (recyclerView.translationY != 0f) {
                        translationAnim = createAnim().also { it.start() }
                    }
                } else {
                    if (recyclerView.translationX != 0f) {
                        translationAnim = createAnim().also { it.start() }
                    }
                }
            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)

                // The list has reached the edge on fling.
                val sign = if (parentDirection == RecyclerView.VERTICAL) {
                    if (direction == DIRECTION_BOTTOM) -1 else 1
                } else {
                    if (direction == DIRECTION_RIGHT) -1 else 1
                }
                val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                translationAnim?.cancel()
                translationAnim = createAnim().setStartVelocity(translationVelocity)?.also { it.start() }
            }

            override fun draw(canvas: Canvas?): Boolean {
                // don't paint the usual edge effect
                return false
            }

            override fun isFinished(): Boolean {
                // Without this, will skip future calls to onAbsorb()
                return translationAnim?.isRunning?.not() ?: true
            }

            private fun createAnim() = SpringAnimation(recyclerView, if (parentDirection == RecyclerView.VERTICAL) SpringAnimation.TRANSLATION_Y else SpringAnimation.TRANSLATION_X).apply {
                spring = SpringForce().apply {
                    finalPosition = 0f
                    dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
                    stiffness = SpringForce.STIFFNESS_MEDIUM
                }
            }

        }
    }
}