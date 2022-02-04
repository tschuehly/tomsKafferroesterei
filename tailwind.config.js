module.exports = {
  content: ["./src/main/resources/templates/*.html"],
  theme: {
    screens: {
      md: '840px'
    },
    extend: {
      spacing: {
        '160': '40rem'
      },
      colors: {
        'brand': {
          DEFAULT: '#4D3B2E',
          '50': '#B39580',
          '100': '#AB8B73',
          '200': '#9A765C',
          '300': '#80624D',
          '400': '#674F3D',
          '500': '#4D3B2E',
          '600': '#2A2019',
          '700': '#070504',
          '800': '#000000',
          '900': '#000000'
        },
      }
    },
  },
  plugins: [],
}
