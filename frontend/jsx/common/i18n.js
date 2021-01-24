import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import detector from 'i18next-browser-languagedetector';

import messages_zh_tw from './i18n/locales/zh_tw.json';
import messages_en_us from './i18n/locales/en_us.json';
import about_zh_tw from './i18n/about/zh_tw.json';
import about_en_us from './i18n/about/en_us.json';
import confirm_en_us from './i18n/confirm/en_us.json';
import confirm_zh_tw from './i18n/confirm/zh_tw.json';
import donate_zh_tw from './i18n/donate/zh_tw.json';
import donate_en_us from './i18n/donate/en_us.json';
import footer_zh_tw from './i18n/footer/zh_tw.json';
import footer_en_us from './i18n/footer/en_us.json';
import home_en_us from './i18n/home/en_us.json';
import home_zh_tw from './i18n/home/zh_tw.json';
import our_story_zh_tw from './i18n/our-story/zh_tw.json';
import our_story_en_us from './i18n/our-story/en_us.json';
import newsletters_zh_tw from './i18n/newsletters/zh_tw.json';
import newsletters_en_us from './i18n/newsletters/en_us.json';
import navigation_zh_tw from './i18n/navigation/zh_tw.json';
import navigation_en_us from './i18n/navigation/en_us.json';
import sponsorship_en_us from './i18n/sponsorship/en_us.json';
import sponsorship_zh_tw from './i18n/sponsorship/zh_tw.json';
import sponsor_info_en_us from './i18n/sponsor-info/en_us.json';
import sponsor_info_zh_tw from './i18n/sponsor-info/zh_tw.json';
import sponsor_thank_you_en_us from './i18n/sponsor-thank-you/en_us.json';
import sponsor_thank_you_zh_tw from './i18n/sponsor-thank-you/zh_tw.json';
import unsubscribe_en_us from './i18n/unsubscribe/en_us.json';
import unsubscribe_zh_tw from './i18n/unsubscribe/zh_tw.json';


i18n
  .use(detector)
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    ns: ['locales', 'about', 'our_story'],
    fallbackLng: 'en',
    defaultNS: 'locales',
    interpolation: {
      // React already does escaping
      escapeValue: false,
    },
    // lng: 'en', // 'en' | 'es'
    resources: {
      en: {
          about: about_en_us,
          confirm: confirm_en_us,
          donate: donate_en_us,
          footer: footer_en_us,
          home: home_en_us,
          locales: messages_en_us,
          our_story: our_story_en_us,
          newsletters: newsletters_en_us,
          navigation: navigation_en_us,
          sponsorship: sponsorship_en_us,
          sponsor_info: sponsor_info_en_us,
          sponsor_thank_you: sponsor_thank_you_en_us,
          unsubscribe: unsubscribe_en_us,
      },
      zh: {
          about: about_zh_tw,
          confirm: confirm_zh_tw,
          donate: donate_zh_tw,
          footer: footer_zh_tw,
          home: home_zh_tw,
          locales: messages_zh_tw,
          our_story: our_story_zh_tw,
          newsletters: newsletters_zh_tw,
          navigation: navigation_zh_tw,
          sponsorship: sponsorship_zh_tw,
          sponsor_info: sponsor_info_zh_tw,
          sponsor_thank_you: sponsor_thank_you_zh_tw,
          unsubscribe: unsubscribe_zh_tw,
      }
  }});

export default i18n