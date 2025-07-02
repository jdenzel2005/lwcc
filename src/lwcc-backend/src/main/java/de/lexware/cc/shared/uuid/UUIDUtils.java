package de.lexware.cc.shared.uuid;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.UUID;

public final class UUIDUtils {
    private static final SecureRandom random = new SecureRandom();

    private UUIDUtils() {
    }

    public static UUID randomV7() {
        byte[] value = randomBytes();
        ByteBuffer buf = ByteBuffer.wrap(value);
        long high = buf.getLong();
        long low = buf.getLong();
        return new UUID(high, low);
    }

    private static byte[] randomBytes() {
        byte[] value = new byte[16];
        random.nextBytes(value);

        ByteBuffer timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(System.currentTimeMillis());

        // Copy 6 bytes starting from index 2 of timestamp into value
        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        // Set version to 7 (the upper 4 bits of byte 6)
        value[6] = (byte) ((value[6] & 0x0F) | 0x70);

        // Set variant bits (the upper 2 bits of byte 8 to 10)
        value[8] = (byte) ((value[8] & 0x3F) | 0x80);

        return value;
    }
}
