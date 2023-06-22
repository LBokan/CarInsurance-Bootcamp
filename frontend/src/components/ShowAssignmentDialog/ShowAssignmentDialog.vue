<template>
  <v-dialog
    v-model="assignment.isDialogOpen"
    persistent
    fullscreen
    transition="dialog-bottom-transition"
  >
    <v-card 
      v-if="(!isLoadingAssignment && isSuccessAssignment) || assignment.id" 
      color="blue-lighten-4"
    >
      <v-toolbar dark color="blue-lighten-1">
        <v-toolbar-title>
          <h1 class="text-h5">Assignment #{{ assignment.id }}</h1>
        </v-toolbar-title>
        <v-spacer />
        <v-btn icon="mdi-close" @click="closeAssignmentInfoPage" />
      </v-toolbar>

      <v-container class="mx-0 w-100 content-wrapper">
        <v-row>
          <v-col cols="12" sm="6">
            <h2 class="text-subtitle-1">Assignment information</h2>
          </v-col>
          <v-divider vertical />
          <v-col cols="12" sm="6">
            <h2 class="text-subtitle-1">Vehicle information</h2>
          </v-col>
        </v-row>
        <v-row class="bg-white">
          <ShowAssignmentInfoCard />
          <v-divider vertical />
          <ShowVehicleInfoCard />
        </v-row>

        <v-row>
          <v-col cols="12" sm="6">
            <h2 class="text-subtitle-1">Contacts information</h2>
          </v-col>
          <v-divider vertical />
          <v-col cols="12" sm="6">
            <h2 class="text-subtitle-1">Vehicle condition information</h2>
          </v-col>
        </v-row>
        <v-row class="bg-white">
          <ShowContactsInfoCard />
          <v-divider vertical />
          <v-col cols="12" sm="6">
            <PreloaderCircle v-if="isLoadingPhotos && !assignment.vehicleConditionInfo.photosOfImpactStrings.length" />
            <v-carousel 
              v-else-if="assignment.vehicleConditionInfo.photosOfImpactStrings.length" 
              height="400" 
              show-arrows="hover"
            >
              <v-carousel-item
                v-for="photo in assignment.vehicleConditionInfo.photosOfImpactStrings"
                :key="photo.toString()"
                :src="photo.toString()"
                @click="openPhoto(photo.toString())"
                alt="Photo of incident"
              />
            </v-carousel>
            <p v-else class="mt-10 text-center">No photos found</p>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
    <v-card v-else>
      <v-toolbar
        dark
        color="blue-lighten-1"
      >
        <v-spacer />
        <v-btn
          icon="mdi-close"
          @click="closeAssignmentInfoPage"
        />
      </v-toolbar>
      <v-container class="mx-0 w-100 content-wrapper">
        <h1 class="mt-10 text-h5 text-center">Assignment is not found</h1>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';
  import { storeToRefs } from 'pinia';

  import { useAssignmentStore } from '@/stores/assignment';

  import ShowAssignmentInfoCard from '@/components/ShowAssignmentDialog/ShowAssignmentInfoCard.vue';
  import ShowVehicleInfoCard from '@/components/ShowAssignmentDialog/ShowVehicleInfoCard.vue';
  import ShowContactsInfoCard from '@/components/ShowAssignmentDialog/ShowContactsInfoCard.vue';
  import PreloaderCircle from '@/components/PreloaderCircle.vue';

  const router = useRouter();
  defineProps({
    isLoadingAssignment: Boolean,
    isSuccessAssignment: Boolean,
    isLoadingPhotos: Boolean
  });
  const emits = defineEmits(['open-photo']);

  const { assignment } = storeToRefs(useAssignmentStore());
  const { closeAndResetAssignmentModalOrDialog } = useAssignmentStore();

  const openPhoto = (photo: string) => {
    emits('open-photo', photo);
  };

  const closeAssignmentInfoPage = () => {
    closeAndResetAssignmentModalOrDialog();
    router.push('/');
  };
</script>

<style scoped>
  .content-wrapper {
    min-width: 100%;
  }
</style>