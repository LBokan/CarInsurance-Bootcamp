<template>
  <v-col 
    v-if="userState.role == ROLES.client" 
    class="assignment-info-item"
    cols="12" 
    sm="6"
  >
    <div>
      <span class="mr-2 text-subtitle-2 font-weight-bold">Status:</span>
      <v-chip
        class="my-2"
        :color="getChipStatusColor(assignment.status)"
        text-color="white"
        :prepend-icon="getChipStatusIcon(assignment.status)"
      >
        {{ assignment.status }}
      </v-chip>
    </div>
    <p>
      <span class="text-subtitle-2">Date of creation:</span>
      {{ formatDate(assignment.creationDate) }}
    </p>
    <p>
      <span class="text-subtitle-2">Date of incident:</span>
      {{ formatDate(assignment.incidentDate) }}
    </p>
    <v-divider class="my-2" />

    <p>
      <span class="text-subtitle-2">Insurance agency:</span>
      {{ assignment.insuranceAgency }}
    </p>
    <p v-if="assignment.repairFacility">
      <span class="text-subtitle-2">Repair facility:</span>
      {{ assignment.repairFacility }}
    </p>
  </v-col>

  <v-col v-else class="assignment-info-item" cols="12" sm="6">
    <v-tabs
      v-model="tab"
      class="mb-5"
      color="blue-darken-1"
      align-tabs="center"
      height="30"
      grow
    >
      <v-tab value="1">General info</v-tab>
      <v-tab value="2">Comments</v-tab>
    </v-tabs>

    <v-window v-model="tab">
      <v-window-item value="1">
        <div>
          <span class="text-subtitle-2 font-weight-bold">Status:</span>
          <v-chip
            class="ml-2"
            :color="getChipStatusColor(assignment.status)"
            text-color="white"
            :prepend-icon="getChipStatusIcon(assignment.status)"
          >
            {{ assignment.status }}
          </v-chip>
        </div>
        <p class="mt-2">
          <span class="text-subtitle-2">Date of creation:</span>
          {{ formatDate(assignment.creationDate) }}
        </p>
        <p>
          <span class="text-subtitle-2">Date of incident:</span>
          {{ formatDate(assignment.incidentDate) }}
        </p>
        <v-divider class="my-2" />

        <p>
          <span class="text-subtitle-2">Insurance agency:</span>
          {{ assignment.insuranceAgency }}
        </p>
        <p v-if="assignment.repairFacility">
          <span class="text-subtitle-2">Repair facility:</span>
          {{ assignment.repairFacility }}
        </p>

        <v-btn
          v-if="assignment.comments?.length && !assignment.repairFacility"
          class="mt-5"
          color="info"
          variant="elevated"
          @click="openSetRepairFacilityModal"
        >
          Set repair facility center
        </v-btn>
      </v-window-item>

      <v-window-item value="2">
        <v-list v-if="assignment.comments?.length" lines="one">
          <v-list-item
            v-for="comment in assignment.comments" 
            :key="comment.id"
            :class="getCommentItemClasses(!!comment.isRead)"
            :title="comment.text"
            :subtitle="formatDateWithTime(comment.dateOfCreation)"
            @click="openCommentModal(comment)"
          />
        </v-list>
        <h1 v-else class="my-5 text-h6 text-center">No comments created</h1>

        <v-btn
          v-if="userState.role == ROLES.insurance" 
          class="mt-2"
          color="info"
          variant="elevated"
          @click="openCreateCommentModal"
        >
          + Add comment
        </v-btn>
      </v-window-item>
    </v-window>
  </v-col>

  <CreateCommentModal 
    v-if="isCreateCommentModalOpen"
    v-model="isCreateCommentModalOpen"
    @close-modal="closeCreateCommentModal"
  />

  <CommentModal
    v-if="isCommentModalOpen"
    v-model="isCommentModalOpen"
    :comment-data="selectedCommentData"
    @close-modal="closeCommentModal"
  />

  <SetRepairFacilityModal 
    v-if="isSetRepairFacilityModalOpen"
    v-model="isSetRepairFacilityModalOpen"
    @close-modal="closeSetRepairFacilityModal"
  />
</template>

<script setup lang="ts">
  import { onMounted, onUnmounted, ref } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames, ROLES } from '@/helpers/constants';
  import { formatDate, formatDateWithTime } from '@/helpers/assignment';
  import { getChipStatusColor, getChipStatusIcon } from '@/helpers/styles';
  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useGetComments } from '@/hooks/useGetComments';

  import CreateCommentModal from '@/components/CreateCommentModal.vue';
  import CommentModal from '@/components/CommentModal.vue';
  import SetRepairFacilityModal from '@/components/SetRepairFacilityModal.vue';

  import type { IComment } from '@/helpers/interfaces';

  const { userState } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());

  const bus = useEventBus<string>(eventBusNames.fetchComments);
  const tab = ref(null);
  const isCreateCommentModalOpen = ref(false);
  const isSetRepairFacilityModalOpen = ref(false);
  const isCommentModalOpen = ref(false);
  const selectedCommentData = ref({});

  const { getComments } = useGetComments();

  const getCommentItemClasses = (isCommentRead: boolean) => {
    let classes = 'comment-item';

    if (userState.value.role == ROLES.repair && !isCommentRead) {
      classes += ' comment-item-unread';
    }

    return classes;
  };
  
  const openCreateCommentModal = () => {
    isCreateCommentModalOpen.value = true;
  };

  const closeCreateCommentModal = () => {
    isCreateCommentModalOpen.value = false;
  };

  const openCommentModal = (data: IComment) => {
    selectedCommentData.value = data;
    isCommentModalOpen.value = true;
  };

  const closeCommentModal = () => {
    isCommentModalOpen.value = false;
  };

  const openSetRepairFacilityModal = () => {
    isSetRepairFacilityModalOpen.value = true;
  };

  const closeSetRepairFacilityModal = () => {
    isSetRepairFacilityModalOpen.value = false;
  };

  const handleBusComments = (busEvent: string) => {
    if (busEvent == 'true') {
      getComments();
    }
  };

  onMounted(async () => {
    bus.on(handleBusComments);

    if (userState.value.role != ROLES.client) {
      await getComments();
    }
  });

  onUnmounted(() => {
    bus.off(handleBusComments);
  });
</script>

<style scoped>
  .assignment-info-item {
    height: 300px;
    overflow-y: auto;
  }

  .comment-item {
    position: relative;
  }

  .comment-item-unread::before {
    content: '';
    position: absolute;
    top: 2px;
    left: 2px;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: red;
  }

  .comment-item:hover {
    cursor: pointer;
  }
</style>