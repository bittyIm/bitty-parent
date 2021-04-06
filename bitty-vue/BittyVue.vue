<template>
    <div>
        <div></div>
        <List v-if="route.path=='list'" />
        <Detail v-if="route.path=='detail'"  />
        <div>
            <a>list</a> | <a>page</a>
        </div>
    </div>
</template>

<script>
import List from "./page/List.vue"
import Detail from "./page/Detail.vue"

console.log(List);

export default {
    name:"bitty",
    components:{
       "List":List,
       "Detail":Detail
    },
    data(){
        return {
            route:{
                path:"list"
            },
            broker:null
        }
    },
    mounted: function () {
        console.log("mounted");
    }
}
</script>

<style>

</style>