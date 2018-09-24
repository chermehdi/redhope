<template>
  <div class="events__container">
    <div class="selection__container">
      <clickable-card link="/dashboard/events/list" text="Manage Event's List" />
      <clickable-card link="/dashboard/events/create" text="Create New Event" />
    </div>
  </div>
</template>
<script>

import ClickableCard from '@/components/ClickableNavigationCard'
import BloodContainer from '@/components/BloodContainer'

export default {
  components: {
    ClickableCard,
    BloodContainer
  },
  data () {
    return {
    }
  }
}
</script>

<style>
  .events__container {
    padding: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .selection__container {
    display: flex;
    flex-wrap: wrap;
    min-height: 30rem;
    width: 100%;
  }
</style>
