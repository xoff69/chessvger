<template>
  Gestion des bd
  <div class="full-page">
 
  <div class="two-thirds-page">
    <div style="margin-bottom: 20px">
    <el-button size="small" @click="addTab(editableTabsValue)">
      add tab
    </el-button>
  </div>
  <el-tabs
    v-model="editableTabsValue"
    type="card"
    class="demo-tabs"
    closable
    @tab-remove="removeTab" @tab-change="clickTab"
  >
    <el-tab-pane
      v-for="item in editableTabs"
      :key="item.name"
      :label="item.title"
      :name="item.name"
    >
    <div >
      {{ item.content }}
      
    </div>
    </el-tab-pane>
  </el-tabs>
  </div></div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import axios from "axios"

let tabIndex = 2
const editableTabsValue = ref('2')
let info=ref('')
const editableTabs = ref([
  {
    title: 'Tab 1',
    name: '1',
    content: 'Tab 1 content',
    
  },
  {
    title: 'Tab 2',
    name: '2',
    content: 'Tab 2 content',
  },
])

const addTab = (targetName: string) => {
  const newTabName = `${++tabIndex}`
  editableTabs.value.push({
    title: 'New Tab',
    name: newTabName,
    content: 'New Tab content',
  })
  editableTabsValue.value = newTabName
}
const clickTab = (targetName: string) => {
  const tabs = editableTabs.value
  console.log(" click tab xxx "+targetName+ Object.values(targetName));
  axios
      .get('https://api.coindesk.com/v1/bpi/currentprice.json')
      .then(response => {
            console.log("reg "+response.data) ;
           console.log(" tab "+targetName)
           tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              tab.content=response.data;
            }
          })
      })

}
const removeTab = (targetName: string) => {
  const tabs = editableTabs.value
  let activeName = editableTabsValue.value
  if (activeName === targetName) {
    tabs.forEach((tab, index) => {
      if (tab.name === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1]
        if (nextTab) {
          activeName = nextTab.name
        }
      }
    })
  }

  editableTabsValue.value = activeName
  editableTabs.value = tabs.filter((tab) => tab.name !== targetName)
}
</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

html, body {
  height: 100%;width: 100%;
  margin: 0;
  padding: 0;
}
.two-thirds-page {
  height: 66.66vh; /* 2/3 de la hauteur de la fenêtre */
  width: 100%; /* 100% de la largeur */
  background-color: #d1e0e0; /* Optionnel : couleur de fond pour visualiser */
  display: flex;
  justify-content: center; /* Optionnel : centrer horizontalement le contenu */
  align-items: center; /* Optionnel : centrer verticalement le contenu */
}
#app {
  min-height: 100vh; 
  display: flex;
  flex-direction: column;
  width: 100%; 
}
.full-page {
  flex: 1;            
  display: flex;         
  justify-content: center; 
  align-items: top;   
  height: 100%;         
  width: 100%; /* Le div occupe toute la largeur */
  background-color: #d1e0e0; /* Optionnel : couleur de fond pour visualisation */
  padding: 20px; /* Optionnel : ajouter de l'espace intérieur */
  text-align: center; /* Optionnel : centrer le texte horizontalement */
}
</style>
