<template>
  <strong>worker</strong>

  <table border="2" style="width: 100%;">
    <tr>
      <td>id</td>
      <td>name</td>
      <td>salary</td>
      <td>startDate</td>
      <td>position</td>
      <td>status</td>
      <td>coordinates x</td>
      <td>coordinates y</td>
      <td>organization</td>
    </tr>
    <tr v-if="cworker">
      <td>{{cworker.id}}</td>
      <td>{{cworker.name}}</td>
      <td>{{cworker.salary}}</td>
      <td>{{cworker.startDate}}</td>
      <td>{{cworker.position}}</td>
      <td>{{cworker.status}}</td>
      <td v-if="cworker.coordinates">{{cworker.coordinates.x}}</td>
      <td v-if="cworker.coordinates">{{cworker.coordinates.y}}</td>
      <td v-if="cworker.organization">{{cworker.organization.id}}</td>
    </tr>
  </table>

</template>

<script>
export default {
  name: "WorkerView",
  props: {
    cworker: []
  }
}
</script>

<style scoped>

table {
  font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
  font-size: 14px;
  border-collapse: collapse;
  text-align: center;
}
th, td:first-child {
  background: #AFCDE7;
  color: white;
  padding: 10px 20px;
}
th, td {
  border-style: solid;
  border-width: 0 1px 1px 0;
  border-color: white;
}
td {
  background: #D8E6F3;
}
th:first-child, td:first-child {
  text-align: left;
}
</style>