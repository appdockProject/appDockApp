import {Page, NavController} from 'ionic-angular';
import {HomePage} from '../home/home';
import {TutorialPage} from '../tutorial/tutorial';
import {DevPage} from '../dev/dev';
import {SocialPage} from '../social/social';
import {OnePage} from '../one/one';
import {TwoPage} from '../two/two';
import {ThreePage} from '../three/three';
import {FourPage} from '../four/four';
import {AboutPage} from '../about/about';
/*
  Generated class for the FeedbackPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/feedback/feedback.html',
})
export class FeedbackPage {
  homePage = HomePage;
  tutorialPage = TutorialPage;
  devPage = DevPage;
  socialPage = SocialPage;
  onePage = OnePage;
  twoPage = TwoPage;
  threePage = ThreePage;
  fourPage = FourPage;
  aboutPage = AboutPage;
  constructor(public nav: NavController) {}
}
