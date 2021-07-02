<template>
  <div class="login row " >
    <div class="col-12 col-sm-6  d-none d-sm-flex flex-column h-100vh">
        <b-img src="./content/images/digestImg/mob_bg_1.svg" fluid alt="Responsive image"
          class="align-self-start w-60"></b-img>
        <div class="position-relative mx-auto mt-5  w-60" >
          <b-img src="./content/images/digestImg/banner_web.svg" fluid alt="Responsive image"></b-img> 
          <div class="script-on-book">
              人活著不是單靠食物，乃是靠神口裏所出的一切話。（馬太福音4：4）
          </div>
        </div>
       
    </div>
     <div class="col-12 d-block d-sm-none "> 
        <div class="position-relative m-auto w-100" > 
          <b-img src="./content/images/digestImg/banner_mob.svg"  
            class="" fluid alt="Responsive image"></b-img>
          <div class="script-on-book-xs">
              人活著不是單靠食物，乃是靠神口裏所出的一切話。
              <div class="text-right">（馬太福音4：4）</div>
          </div>
        </div>
        <b-img src="./content/images/digestImg/web_bg_2.svg" 
          class="align-self-end w-40 d-none d-xs-block"  fluid alt="Responsive image"></b-img>
    </div>
     <div class="col-12 col-sm-6 position-relative mt-5">
       <login-form></login-form>
       <div class="right-left-bottom d-none d-sm-block  "> 
          <b-img src="./content/images/digestImg/web_bg_2.svg"  fluid alt="Responsive image"
            class="w-100"></b-img>
       </div>
    </div>
   

  </div>
</template>

<script lang="ts" src="./login.component.ts"></script>

<style scoped>
  .script-on-book{
    font-size:1.2em;
    font-weight:500;
    position: absolute;
    top: 70%;  
    padding: 0 15.5% 0 12%;
    color:rgba(80,80,80,1);
  }
  .script-on-book-xs{
    font-size:1.2em;
    font-weight:400;
    position: absolute;
    top: 40%;  
    padding: 0 13% 0 15%;
    color:rgba(80,80,80,1);
  }

  .right-left-bottom{
     position: absolute;
     bottom:0;
     right:15px;
  }
</style>