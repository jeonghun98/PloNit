import { useState } from "react";
import imageCompression from "browser-image-compression";

const useImageCompress = () => {
  const [isLoading, setIsLoading] = useState(false);

  const compressImage = async (imageFile: File) => {
    if (isLoading) return;

    setIsLoading(true);
    const options = {
      maxSizeMB: 4,
      maxWidthOrHeight: 1024,
    };

    try {
      const compressedFile = await imageCompression(imageFile, options);
      setIsLoading(false);

      return compressedFile;
    } catch (error) {
      setIsLoading(false);
      console.error(error);
    }
  };

  return { compressImage, isLoading };
};

export default useImageCompress;
