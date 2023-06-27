<template>
  <v-dialog persistent width="800">
    <v-card>
      <v-card-title>
        <p class="text-h6">Comment data</p>
      </v-card-title>

      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-textarea
                v-model="commentValue"
                label="Comment"
                readonly
              />
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-divider />

      <v-card-actions class="p-20">
        <v-spacer/>
        <v-btn
          color="error"
          rounded="lg"
          variant="elevated"
          @click="closeModal"
        >
          Close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { onMounted, ref } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames, ROLES } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';
  import { useSetIsCommentRead } from '@/hooks/useSetIsCommentRead';
  
  const bus = useEventBus<boolean>(eventBusNames.fetchComments);
  const emits = defineEmits(['close-modal']);
  const props = defineProps({
    commentData: {
      type: Object,
      default: () => {}
    }
  });
  const commentValue = ref(props.commentData.text);
  
  const { userRole } = storeToRefs(useUserStore());
  
  const { setIsCommentRead } = useSetIsCommentRead();

  onMounted(async () => {
    if (userRole.value === ROLES.repair) {
      await setIsCommentRead(props.commentData.id);

      bus.emit(true);
    }
  })

  const closeModal = () => {
    emits('close-modal');
  };
</script>