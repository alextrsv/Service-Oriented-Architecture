<template>
  <li v-on:click="$emit('show-worker-info', worker)">
    <span>
      <input type="checkbox">
      <strong>{{worker.id}}</strong>
      {{worker.name}}
    </span>
<!--    <button v-on:click="$emit('remove-worker', worker.id)" class="rm">&times;</button>-->
  </li>
</template>

<script>
export default {
  name: "Worker",
  props: {
    worker: {
      type : Object,
      required: true
    }
  }
}
</script>

<style scoped>
li {
  border: 1px solid #ccc;
  display: flex;
  justify-content: space-between;
  padding: .5rem 2rem;
  width: 50%
}

.rm {
  background: red;
  color: #fff;
  border-radius: 50%;
  font-weight: bold;
}
 input {
   margin-right: 1rem;
 }

</style>