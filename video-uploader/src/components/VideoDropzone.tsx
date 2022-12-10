import React from 'react';
import { Box } from '@material-ui/core';
import { Button, Stack } from "@mui/material";
import { DropzoneArea } from 'material-ui-dropzone';

const VideoDropzone = () => {
  return (
    <Box>
      <DropzoneArea
        dropzoneText={'Drag and drop a video here or click'}
        maxFileSize={500000000}
        onChange={(files) => console.log('Files', files)}
        showFileNames={true}
      />
      <Stack alignItems='center' sx={{ mt: 8 }}>
        <Button variant='contained' component='label'>
          Upload
        </Button>
      </Stack>
    </Box>
  );
}

export { VideoDropzone };
