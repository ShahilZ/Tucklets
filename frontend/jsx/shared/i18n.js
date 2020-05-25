import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import messages_zh_tw from './locales/zh_tw.json';
import messages_en_us from './locales/en_us.json';

const messages = {
    'zh_TW': messages_zh_tw,
    'en_US': messages_en_us
};

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    ns: ['locales'],
    defaultNS: 'locales',
    interpolation: {
      // React already does escaping
      escapeValue: false,
    },
    lng: 'en', // 'en' | 'es'
    resources: {
      en: {
          locales: messages_en_us
      },
      zh: {
          locales: messages_zh_tw
      }
  }});

export default i18n