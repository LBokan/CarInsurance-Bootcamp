<template>
  <v-col cols="12" sm="6">
    <p>
      <span class="text-subtitle-2">Car make:</span>
      {{ assignment.vehicleInfo.carMake }}
    </p>
    <p>
      <span class="text-subtitle-2">Car model:</span>
      {{ assignment.vehicleInfo.carModel }}
    </p>
    <p>
      <span class="text-subtitle-2">Year of manufacture:</span>
      {{ assignment.vehicleInfo.yearOfManufacture }}
    </p>

    <v-divider class="my-2" />

    <p>
      <span class="text-subtitle-2">VIN number:</span>
      {{ assignment.vehicleInfo.vinNumber.toUpperCase() }}
    </p>
    <p>
      <span class="text-subtitle-2">Odometer value:</span>
      {{ formatNumber(assignment.vehicleInfo.odometerValue) }}
      <span class="text-subtitle-2">km</span>
    </p>

    <v-divider class="my-2" />

    <p>
      <span class="text-subtitle-2">License plate number:</span>
      {{ assignment.vehicleInfo.licensePlateNumber.toUpperCase() }}
    </p>
    <p>
      <span class="text-subtitle-2">License state:</span>
      {{ assignment.vehicleInfo.licenseState }}
    </p>
    <p>
      <span class="text-subtitle-2">License expiration date:</span>
      {{ formatDate(assignment.vehicleInfo.licenseExpirationDate) }}
    </p>
  </v-col>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { formatDate } from '@/helpers/assignment';
  import { useAssignmentStore } from '@/stores/assignment';

  const { assignment } = storeToRefs(useAssignmentStore());

  const formatNumber = (number: string | number) => {
    return parseInt(number.toString()).toLocaleString('en-US');
  }
</script>