import { defineConfig } from 'vitest/config'

export default defineConfig({
  test: {
      exclude: [],
      setupFiles: ["tests/setup.js"]
  },
})
