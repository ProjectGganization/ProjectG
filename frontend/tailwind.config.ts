import type { Config } from 'tailwindcss';

const config: Config = {
  darkMode: 'class',
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        'on-primary': '#edeeff',
        'outline-variant': '#b1b1bc',
        'error': '#a8364b',
        'surface-container-lowest': '#ffffff',
        'surface-variant': '#e2e1ed',
        'on-surface': '#31323b',
        'surface-dim': '#d9d9e4',
        'on-primary-container': '#ffffff',
        'secondary': '#526074',
        'outline': '#797a84',
        'tertiary-container': '#ecccfb',
        'background': '#fbf8fe',
        'on-secondary-container': '#455367',
        'surface-bright': '#fbf8fe',
        'on-surface-variant': '#5d5e68',
        'on-error': '#fff7f7',
        'primary-container': '#0052fe',
        'primary': '#0048e2',
        'secondary-container': '#d5e3fc',
        'surface-container-high': '#e8e7f1',
        'tertiary': '#6f567d',
        'surface-container': '#eeedf6',
        'on-background': '#31323b',
        'surface-container-low': '#f4f2fb',
        'surface-container-highest': '#e2e1ed',
        'surface': '#fbf8fe',
        'on-secondary': '#f8f8ff',
      },
      fontFamily: {
        headline: ['Inter', 'sans-serif'],
        body: ['Inter', 'sans-serif'],
        label: ['Inter', 'sans-serif'],
      },
      borderRadius: {
        DEFAULT: '0.125rem',
        md: '0.375rem',
        lg: '0.25rem',
        xl: '0.5rem',
        full: '0.75rem',
      },
      boxShadow: {
        // Ambient shadow: 24px blur, 4% opacity, on-surface tint
        ambient: '0 4px 24px rgba(49, 50, 59, 0.04)',
        'ambient-lg': '0 8px 24px rgba(49, 50, 59, 0.08)',
      },
    },
  },
  plugins: [],
};

export default config;
