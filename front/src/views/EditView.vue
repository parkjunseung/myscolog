<script setup lang="ts">
import {defineProps, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  }
});

const post = ref({
  id:0,
  title: '',
  content: "",
});


axios.get(`/api/posts/${props.postId}`)
  .then((res) => {
    post.value = res.data;
  });

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value)
    .then((res) => {
      console.log(res)
      router.replace({name:"home"});
    });
}


</script>

<template>
  <div>
    <el-input v-model="post.title" placeholder="제목을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="post.content"  type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>

<style>

</style>
