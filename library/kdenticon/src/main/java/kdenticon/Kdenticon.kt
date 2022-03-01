package kdenticon

class Kdenticon {

    companion object {
        /**
         * Draws an identicon as an SVG string.
         * @param hash - A hexadecimal hash string or any value that will be hashed by Jdenticon.
         * @param size - Icon size in pixels.
         * @param padding - Optional padding in percents. Extra padding might be added to center the rendered identicon.
         * @returns SVG string
         */
        fun toSvg(hash: String, size: Int, padding: Float? = null): String {
            val writer = SvgWriter(size)
            var renderer = SvgRenderer(writer)
            IconGenerator(
                    renderer,
                    hash,
                    0f,
                    0f,
                    size.toFloat(),
                    padding,
                    getCurrentConfig()
            )
            return writer.toString()
        }

        /**
         * Gets the normalized current Jdenticon color configuration. Missing fields have default values.
         */
        private fun getCurrentConfig(): Config {
            val backColor = "#FFFFFF"
            /**
             * Creates a lightness range.
             */
            fun lightness(configName: String, defaultMin: Float, defaultMax: Float): (Float) -> Float {
                val range = arrayOf(defaultMin, defaultMax)

                /**
                 * Gets a lightness relative the specified value in the specified lightness range.
                 */
                return fun(value: Float): Float {
                    val value2 = range[0] + value * (range[1] - range[0])
                    return if (value2 < 0) 0f else if (value2 > 1) 1f else value2
                }
            }

            return Config(
                    0.5f,
                    lightness("color", 0.4f, 0.8f),
                    lightness("grayscale", 0.3f, 0.9f),
                    Color.parse(backColor)
            )
        }
    }
}