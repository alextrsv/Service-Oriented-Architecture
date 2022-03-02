<template>

  <div>
    <div style="margin-bottom: 2rem">
      <label for="page">page</label>
      <input type="number" id="page"  v-model="page" :placeholder="page">
      <label for="page">size</label>
      <input type="number" id="size"  v-model="size" :placeholder="size">

      <label for="id">id :</label>
      <input type="text" id="id"  v-model="id" :placeholder="id">

      <label for="salary">salary:</label>
      <input type="text" id="salary"  v-model="salary" :placeholder="salary">

      <label for="name">name:</label>
      <input type="text" id="name"  v-model="name" :placeholder="name">

      <label for="position">position:</label>
      <input type="text" id="position"  v-model="position" :placeholder="position">

      <label for="positionFilter">positionFilter:</label>
      <input type="text" id="positionFilter"  v-model="positionFilter" :placeholder="positionFilter">


      <br/>
      <label for="status">status:</label>
      <input type="text" id="status"  v-model="status" :placeholder="status">

      <label for="statusFilter">statusFilter:</label>
      <input type="text" id="statusFilter"  v-model="statusFilter" :placeholder="statusFilter">


      <label for="xCoord">xCoord:</label>
      <input type="text" id="xCoord"  v-model="xCoord" :placeholder="xCoord">

      <label for="yCoord">yCoord:</label>
      <input type="text" id="yCoord"  v-model="yCoord" :placeholder="yCoord">

      <label for="organization">organization:</label>
      <input type="text" id="organization" v-model="organization" :placeholder="organization">

      <label for="startDate">startDate:</label>
      <input type="text" id="startDate"  v-model="startDate" :placeholder="startDate">

      <!--      сортировка-->
      <label for="sort">sort</label>
      <input type="text" id="sort"  v-model="sort" :placeholder="sort">


      <button style="margin-left: 2rem" v-on:click="getWorkers" class="rm">поиск</button>
      <button style="margin-left: 2rem" v-on:click="addWorker" class="rm">добавить</button>
      <button style="margin-left: 2rem" v-on:click="removeWorker" class="rm">удалить</button>
      <button style="margin-left: 2rem" v-on:click="changeWorker" class="rm">изменить</button>
      <button style="margin-left: 2rem" v-on:click="updateWorker" class="rm">обновить</button>
      <button style="margin-left: 2rem" v-on:click="$emit('get-max-pos')" class="rm">maxPos</button>
      <button style="margin-left: 2rem" v-on:click="startDateAmount" class="rm">startDateAmount</button>
      <button style="margin-left: 2rem" v-on:click="getAmountByStatus" class="rm">AmountByStatus</button>
      <!--      <button style="margin-left: 2rem" v-on:click="updateWorker" class="rm">обновить</button>-->
      <!--      <button style="margin-left: 2rem" v-on:click="updateWorker" class="rm">обновить</button>-->
      <button style="margin-left: 2rem" v-on:click="clearFields" class="rm">очистить</button>

    </div>
    <div>
      <ul>
        <Worker
            v-for="(worker, index) in workers"
            v-bind:worker="worker"
            :key="index"
            v-on:show-worker-info="showWorkerInfo"
        />
      </ul>
    </div>
    <div>
      <h1>{{message}}</h1>
    </div>
  </div>
</template>

<script>
import Worker from "@/components/Worker";
export default {
  name: "WorkersList",
  components: {
    Worker
  },
  props: ['workers', 'message'],
  data() {
    return {
      page: 0,
      size: 10,
      id: '',
      salary: '',
      name: '',
      position: '',
      status: '',
      xCoord: '',
      yCoord: '',
      startDate: '',
      organization: '',
      sort: '',
      currentWorker: {},
      params: [],
      filterParams: [],
      sortParams: '',
      positionFilter: '',
      statusFilter: '',

      updWorker: {},

      addParams: [],
      updParams: []
    }
  },
  methods: {
    clearFields(){
      this.id = ''
      this.salary = ''
      this.name = ''
      this.position = ''
      this.status = ''
      this.xCoord = ''
      this.yCoord = ''
      this.organization = ''
      this.startDate  = ''
    },
    changeWorker(){
      this.id = this.updWorker.id
      this.salary = this.updWorker.salary
      this.name = this.updWorker.name
      this.position = this.updWorker.position
      this.status = this.updWorker.status
      this.xCoord = this.updWorker.coordinates.x
      this.yCoord = this.updWorker.coordinates.y
      this.organization = this.updWorker.organization.id
      this.startDate  = this.updWorker.startDate
    },

    updateWorker(){

      console.log('adding new ')
      this.addParams = []


      if (this.id !== '') this.updParams.push('id='.concat(this.id))
      if (this.salary !== '') this.updParams.push('salary='.concat(this.salary))
      if (this.name !== '') this.updParams.push('name='.concat(this.name))
      if (this.position !== '') this.updParams.push('position='.concat(this.position)) //position=value
      if (this.status !== '') this.updParams.push('status='.concat(this.status)) // status=value
      if (this.xCoord !== '') this.updParams.push('x-coord='.concat(this.xCoord)) // </>/=value
      if (this.yCoord !== '') this.updParams.push('y-coord='.concat(this.yCoord)) // </>/=value
      if (this.organization !== '') this.updParams.push('organizationId='.concat(this.organization)) // </>/=value
      if (this.startDate !== '') this.updParams.push('startDate='.concat(this.startDate)) // </>/=value

      console.log('updParam amount: ' + this.updParams.length)
      this.$emit('upd-workers',this.updParams.length, this.updParams)

      this.salary = ''
      this.name = ''
      this.position = ''
      this.status = ''
      this.xCoord = ''
      this.yCoord = ''
      this.organization = ''
      this.startDate  = ''

    },
    removeWorker(){
      if (this.currentWorker !== null)
        this.$emit('remove-worker')
    },
    showWorkerInfo(worker){
      this.currentWorker = worker
      this.updWorker = worker
      console.log('worker: ' + worker.name)
      console.log('currentWorker: ' + this.currentWorker.name)
      this.$emit('show-worker-info', worker)
    },

    addWorker(){
      console.log('adding new ')
      this.addParams = []

      if (this.salary !== '') this.addParams.push('salary='.concat(this.salary))
      if (this.name !== '') this.addParams.push('name='.concat(this.name))
      if (this.position !== '') this.addParams.push('position='.concat(this.position)) //position=value
      if (this.status !== '') this.addParams.push('status='.concat(this.status)) // status=value
      if (this.xCoord !== '') this.addParams.push('x-coord='.concat(this.xCoord)) // </>/=value
      if (this.yCoord !== '') this.addParams.push('y-coord='.concat(this.yCoord)) // </>/=value
      if (this.organization !== '') this.addParams.push('organizationId='.concat(this.organization)) // </>/=value
      if (this.startDate !== '') this.addParams.push('startDate='.concat(this.startDate)) // </>/=value

      console.log('addsParam amount: ' + this.addParams.length)
      this.$emit('add-workers',this.addParams.length, this.addParams)

      this.salary = ''
      this.name = ''
      this.position = ''
      this.status = ''
      this.xCoord = ''
      this.yCoord = ''
      this.organization = ''
      this.startDate  = ''
    },

    getWorkers(){
      this.params = [];
      this.filterParams = []

      if (this.id !== '') {
        this.params.push('/'.concat(this.id)) // </>/=value
        this.$emit('get-workers',this.params.length, this.filterParams.length,
            this.params, this.filterParams, this.sortParams)
      }
      else {
        if (this.page !== '') this.params.push('page='.concat(this.page))
        if (this.size !== '') this.params.push('size='.concat(this.size))
        if (this.salary !== '') this.filterParams.push('salary'.concat(this.salary)) // </>/=value
        if (this.name !== '') this.filterParams.push('name='.concat(this.name)) //name=value
        if (this.positionFilter !== '') {
          // let positionString = ''
          if (this.positionFilter === 'MANAGER') this.positionFilter = '0';
          else if (this.positionFilter === 'ENGINEER') this.positionFilter = '1';
          else if (this.positionFilter === 'BAKER') this.positionFilter = '2';
          else if (this.positionFilter === 'MANAGER_OF_CLEANING') this.positionFilter = '3';
          // else tghismessage = 'no sush position'
          this.filterParams.push('position=\'' + this.positionFilter + '\'') //position=value
        }
        if (this.status !== '') this.filterParams.push('status='.concat(this.status)) // status=value
        if (this.xCoord !== '') this.filterParams.push('coordinates.x'.concat(this.xCoord)) // </>/=value
        if (this.yCoord !== '') this.filterParams.push('coordinates.y'.concat(this.yCoord)) // </>/=value
        if (this.organization !== '') this.filterParams.push('organization.id'.concat(this.organization)) // </>/=value

        if (this.sort !== '') this.sortParams = 'sort=' + this.sort

        console.log("filterParams:")
        this.filterParams.forEach(p => console.log(p))

        this.$emit('get-workers', this.params.length, this.filterParams.length,
            this.params, this.filterParams, this.sortParams)
      }

    },

    startDateAmount(){
      if (this.startDate !== '') this.filterParams.push('startDate='.concat(this.startDate)) // </>/=value
      this.filterParams.forEach(p => console.log(p))
      this.$emit('get-amount-by-date', this.filterParams)
      this.filterParams = []
    },

    getAmountByStatus(){
      if (this.statusFilter !== '') {
        // let positionString = ''
        if (this.statusFilter === 'FIRED') this.statusFilter = '0';
        else if (this.statusFilter === 'HIRED') this.statusFilter = '1';
        else if (this.statusFilter === 'RECOMMENDED_FOR_PROMOTION') this.statusFilter = '2';
        else if (this.statusFilter === 'REGULAR') this.statusFilter = '3';
        else if (this.statusFilter === 'PROBATION') this.statusFilter = '4';
        // else tghismessage = 'no sush position'
        this.filterParams.push('status=\'' + this.statusFilter+'\'') //position=value

        this.$emit('get-amount-by-status', this.filterParams)
        this.filterParams = []
      }
    }
  }
}
</script>

<style scoped>

ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
input {
  margin: 4px;
}


</style>