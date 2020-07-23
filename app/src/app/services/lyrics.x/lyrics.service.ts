import { Injectable } from '@angular/core';
import { Lyrics } from 'src/app/models/lyrics';
import { ZonedDateTime } from '@js-joda/core'

@Injectable({
  providedIn: 'root'
})
export class LyricsService {

  private lyrics: Lyrics[] = [
    {
      id: 1,
      songId: 1,
      lyrics: `
<b>The First Thing I See</b>

I saw two stars on a cold autumn night
You said that they were the headlights of an alien car
Coming to tell us that we'll be alright

Remember you told me that love isn't blind
It's more like you're watching the world
Through a new set of eyes
Finding the colors that usually hide

Oh I don't know where I'm going
I don't know where I'll be
But it doesn't matter
'Cause every morning
When I wake you're the first thing I see

Sometimes it's hard to be still in your skin
The questions and outcomes can shake you
And spin you and then
You're back on the floor and you're back in your head

But if I'm the ocean then you are the tide
Taking my heart in your hands
As we move through this life
Greeting the moon hung in the sky

Oh I don't know where I'm going
I don't know where I'll be
But it doesn't matter
'Cause every morning
When I wake you're the first thing I see
`,
      lang: 'en',
      author: 'Imaginary Future',
      copyright: '',
      isTranslated: false,
      translatedBy: '',
      charset: 'UTF-8',
      lastModified: ZonedDateTime.now(),
    },

    {
      id: 2,
      songId: 1,
      lyrics: `
<b>睁眼看见的是你</b>

在一个冰冷的秋天的夜晚，我看见两颗星星
你说那是外星人的车灯
他们来告诉我们，我们没事的

记得你告诉我，爱不是盲目的
它更像你用新眼光看世界
发现那些通常看不见的颜色

Oh 我不知道我要去哪里
我也不知道我会在什么地方停下
但没关系
因为每天早晨
我醒来第一个看见的是你

有时候很难静静地呆在你的皮囊之下
问题和答案会把你掀翻
让你打转，然后
你跌到地板上，退回你的脑海

但如果我是海洋，那你就是潮水
把我的心放在你的手里
就这样走过一生
迎着悬挂在天的月亮

Oh 我不知道我要去哪里
我也不知道我会在什么地方停下
但没关系
因为每天早晨
我醒来第一个看见的是你
`,
      lang: 'zh-CN',
      author: 'Imaginary Future',
      copyright: '',
      isTranslated: false,
      translatedBy: 'Yizhuan Yu',
      charset: 'UTF-8',
      lastModified: ZonedDateTime.now(),
    },
    
    {
      id: 3,
      songId: 2,
      lyrics: `
      <b>陪你一起变老</b>

      院子里开满了玫瑰
      
      挂满了葡萄
      
      我们坐在摇摇椅 开心的笑
      
      你说我们永远这么相爱 好不好
      
      我不说话只是紧紧依偎在你怀抱
      
      空中有几朵白云在飘和对对成双的鸟
      
      我们相偎又相依 幸福的笑
      
      我把一生一世爱你的心准备好
      
      不管未来有多艰辛我是你的依靠
      
      我要陪着你一起变老
      
      你就是我最疼爱的宝
      
      我愿和你聊到整夜不睡觉
      
      喜欢看你的撒娇
      
      我要陪着你一起变老
      
      让爱停在这时光隧道
      
      我会珍惜和你的每分每秒
      
      直到海枯石烂天荒地老
      
      空中有几朵白云在飘和对对成双的鸟
      
      我们相偎又相依 幸福的笑
      
      我把一生一世爱你的心准备好
      
      不管未来有多艰辛我是你的依靠
      
      我要陪着你一起变老
      
      你就是我最疼爱的宝
      
      我愿和你聊到整夜不睡觉
      
      喜欢看你的撒娇
      
      我要陪着你一起变老
      
      让爱停在这时光隧道
      
      我会珍惜和你的每分每秒
      
      直到海枯石烂天荒地老
      
      我要陪着你一起变老
      
      你就是我最疼爱的宝
      
      我愿和你聊到整夜不睡觉
      
      喜欢看你的撒娇
      
      我要陪着你一起变老
      
      让爱停在这时光隧道
      
      我会珍惜和你的每分每秒
      
      直到海枯石烂天荒地老
      
      我会珍惜和你的每分每秒
      
      直到海枯石烂天荒地老
      
      `,
      lang: 'zh-CN',
      author: 'Imaginary Future',
      copyright: '',
      isTranslated: false,
      translatedBy: 'Yizhuan Yu',
      charset: 'UTF-8',
      lastModified: ZonedDateTime.now(),
    },
  ];

  constructor() { 
          
  }

  getLyrics(songId: number, lang: string) {
    return {...this.lyrics.find(
      ly => {
        return ly.songId == songId && ly.lang == lang;
      }
    )};
  }

  getLanguages(songId: number) {
    let languages: string[] = [];
    this.lyrics.filter( ly => {
      return ly.songId == songId;
    }).forEach( ly => {
      languages.push(ly.lang);
    });
    return languages;
  }
}
