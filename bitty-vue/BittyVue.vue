<template>
    <div>
        hello bitty Vue
    </div>
</template>

<script>
export default {
    name:"bitty",
    data(){
        return {
            broker:null
        }
    },
    mounted: function () {
        console.log("mounted");
    }
}
</script>