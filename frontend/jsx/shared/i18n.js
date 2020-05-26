import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import messages_zh_tw from './locales/zh_tw.json';
import messages_en_us from './locales/en_us.json';
import about_zh_tw from './about/zh_tw.json';
import about_en_us from './about/en_us.json';

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    ns: ['locales', 'about'],
    defaultNS: 'locales',
    interpolation: {
      // React already does escaping
      escapeValue: false,
    },
    lng: 'en', // 'en' | 'es'
    resources: {
      en: {
          locales: messages_en_us,
          about: about_en_us
      },
      zh: {
          locales: messages_zh_tw,
          about: about_zh_tw
      }
  }});

export default i18n