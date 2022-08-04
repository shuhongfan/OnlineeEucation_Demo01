import Vue from 'vue'
import VueAwesomeSwiper from 'vue-awesome-swiper'
import Element from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN'
// import style
import 'swiper/css/swiper.css'

Vue.use(Element, { locale })
Vue.use(VueAwesomeSwiper, /* { default options with global component } */)
